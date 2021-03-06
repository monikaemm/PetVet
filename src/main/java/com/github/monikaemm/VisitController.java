package com.github.monikaemm;

import org.springframework.jdbc.core.JdbcTemplate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monika Hołysz on 11.06.2016.
 */
public class VisitController {
    public ModelAndView visitsList(Request req, Response res) {
        Map<String, Object> registering_visitModel = new HashMap<>();
        User user = req.session().attribute("user");
        registering_visitModel.put("visits", readFromDb(user));
        registering_visitModel.put("user", user);
        return new ModelAndView(registering_visitModel, "visit");
    }

    public Object registerVisit(Request req, Response res) {
        Visit visit = readVisit(req);
        validateVisit(visit);

        User user = req.session().attribute("user");
        saveToDb(visit, user);

        res.redirect("/visit");

        return null;
    }

    private Visit readVisit(Request req) {
        Visit visit = new Visit();
        visit.setName(req.queryParams("name"));
        visit.setSpecies(req.queryParams("species"));
        visit.setPurpose(req.queryParams("purpose"));
        visit.setDate(getDate(req));
        return visit;
    }

    private void validateVisit(Visit visit) {
        DayOfWeek dayOfWeek = visit.getDate().getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
            throw new IllegalArgumentException("Expected working day");
        }
        int visitColliding = countVisitsAt(visit.getDate());
        if(visitColliding != 0){
            throw new IllegalArgumentException("Expected free slot");
        }
    }

    private LocalDateTime getDate(Request req) {

        int year = Integer.parseInt(req.queryParams("year"));
        int month = Integer.parseInt(req.queryParams("month"));
        int day = Integer.parseInt(req.queryParams("day"));
        int hour = Integer.parseInt(req.queryParams("hour"));
        int minutes = Integer.parseInt(req.queryParams("minutes"));

        return LocalDateTime.of(year, month, day, hour, minutes);
    }

    public ModelAndView registrationView(Request req, Response res){
        Map<String, Object> visitModel = new HashMap<>();
        return new ModelAndView(visitModel, "registering_visit");
    }

    private static List<Visit> readFromDb(User user) {
        JdbcTemplate template = DbAccess.getTemplate();
        return template.query("select visitDate, name, species, purpose from visits where user_id = ? order by visitDate", (rs, rowNum) -> {
            Visit visit = new Visit();
            visit.setDate(rs.getTimestamp(1).toLocalDateTime());
            visit.setName(rs.getString(2));
            visit.setSpecies(rs.getString(3));
            visit.setPurpose(rs.getString(4));
            return visit;
        }, user.getId());
    }

    private static void saveToDb(Visit visit, User user) {
        JdbcTemplate template = DbAccess.getTemplate();
        template.update("insert into visits(visitDate, name, species, purpose, user_id) values(?,?,?,?,?)",
                visit.getDate(),
                visit.getName(),
                visit.getSpecies(),
                visit.getPurpose(),
                user.getId());

    }
    private static int countVisitsAt(LocalDateTime dateTime){
        JdbcTemplate template = DbAccess.getTemplate();
        return template.queryForObject("SELECT COUNT(*) FROM visits WHERE visitDate=?",Integer.class,dateTime);
    }
}
