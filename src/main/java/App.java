import com.sun.org.apache.regexp.internal.RE;
import dao.CodeSchoolDao;
import dao.ReviewDao;
import dao.Sql2oCodeSchoolDao;
import dao.Sql2oReviewDao;
import models.CodeSchool;
import models.Review;
import org.eclipse.jetty.http.HttpStatus;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

/**
 * Created by Guest on 8/16/17.
 */
public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        String connectionString = "jdbc:h2:~/codeschool.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oCodeSchoolDao codeSchoolDao = new Sql2oCodeSchoolDao(sql2o);
        Sql2oReviewDao reviewDao = new Sql2oReviewDao(sql2o);

        //CODESCHOOL Routes

        //get: delete all codeSchools
        get("/codeschools/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            codeSchoolDao.clearAllCodeSchools();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show all Codeschools
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<CodeSchool> codeSchools = codeSchoolDao.getAll();
            model.put("codeSchools", codeSchools);
            return new ModelAndView(model, "codeSchool-index.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show new Codeschool Form
        get("/codeschools/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "codeSchool-form.hbs");
        }, new HandlebarsTemplateEngine());


        //put: process new Codeschool Form
        post("/codeschools/new", (request, response) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            CodeSchool newCodeSchool = new CodeSchool(name);
            codeSchoolDao.add(newCodeSchool);
            model.put("newCodeSchool", newCodeSchool);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show individual codeSchool
        get("/codeschools/:codeschool_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategory = Integer.parseInt(req.params("codeschool_id"));
            CodeSchool foundCodeSchool = codeSchoolDao.findById(idOfCategory);
            model.put("codeschool", foundCodeSchool);
            return new ModelAndView(model, "codeSchool-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update individual codeSchool
        get("/codeschools/:codeschool_id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCodeSchoolToEdit = Integer.parseInt(req.params("codeschool_id"));
            CodeSchool editCodeSchool = codeSchoolDao.findById(idOfCodeSchoolToEdit);
            model.put("editCodeSchool", editCodeSchool);
            return new ModelAndView(model, "codeSchool-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process an update to an individual codeschool
        post("/codeschools/:codeschool_id/update", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String newName = req.queryParams("name");
            int codeSchoolId = Integer.parseInt(req.params("codeschool_id"));
            codeSchoolDao.findById(codeSchoolId);
            codeSchoolDao.update(codeSchoolId, newName);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        //get: delete individual codeschool
        get("/codeschools/:codeschool_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCodeSchoolToDelete = Integer.parseInt(req.params("codeschool_id")); //pull id - must match route segment
            codeSchoolDao.findById(idOfCodeSchoolToDelete); //use it to find task
            codeSchoolDao.deleteById(idOfCodeSchoolToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        // REVIEW Routes

        //get: show a form to add a new review
        get("/codeschools/:codeschool_id/reviews", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCodeSchoolToReview = Integer.parseInt(req.params("codeschool_id"));
            CodeSchool codeschool = codeSchoolDao.findById(idOfCodeSchoolToReview);
            model.put("codeschool", codeschool);
            List<Review> reviews = reviewDao.findByCodeSchoolId(idOfCodeSchoolToReview);
            model.put("reviews",reviews);
            return new ModelAndView(model, "review-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to add a new review
        post("/codeschools/:codeschool_id/reviews", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String content = request.queryParams("content");
            int rating = Integer.parseInt(request.queryParams("rating"));
            int codeSchoolId = Integer.parseInt(request.params("codeschool_id"));
            Review newReview = new Review(content,rating,codeSchoolId);
            reviewDao.add(newReview);
            model.put("review", newReview);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());




    }
}
