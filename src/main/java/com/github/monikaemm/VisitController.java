package com.github.monikaemm;

import org.springframework.jdbc.core.JdbcTemplate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

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
        registering_visitModel.put("visits", readFromDb());
        registering_visitModel.put("user", req.session().attribute("user"));
        return new ModelAndView(registering_visitModel, "visit");
    }

    public Object registerVisit(Request req, Response res) {
        Visit visit = new Visit();
        visit.setName(req.queryParams("name"));
        visit.setSpecies(req.queryParams("species"));
        visit.setPurpose(req.queryParams("purpose"));
        visit.setDate(LocalDateTime.parse(req.queryParams("date"), Visit.formatter));

        saveToDb(visit);

        res.redirect("/visit");

        return null;
    }

    public ModelAndView registrationView(Request req, Response res){
        Map<String, Object> visitModel = new HashMap<>();
        return new ModelAndView(visitModel, "registering_visit");
    }

    private static List<Visit> readFromDb() {
        JdbcTemplate template = DbAccess.getTemplate();
        return template.query("select visitDate, name, species, purpose from visits order by visitDate", (rs, rowNum) -> {
            Visit visit = new Visit();
            visit.setDate(rs.getTimestamp(1).toLocalDateTime());
            visit.setName(rs.getString(2));
            visit.setSpecies(rs.getString(3));
            visit.setPurpose(rs.getString(4));
            return visit;
        });
    }

    private static void saveToDb(Visit visit) {
        JdbcTemplate template = DbAccess.getTemplate();
        template.update("insert into visits(visitDate, name, species, purpose) values(?,?,?,?)",
                visit.getDate(),
                visit.getName(),
                visit.getSpecies(),
                visit.getPurpose());
    }
}
