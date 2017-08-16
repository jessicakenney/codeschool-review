package dao;

import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oReviewDao implements ReviewDao {

    private final Sql2o sql2o;

    public Sql2oReviewDao (Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Review review) {
        String sql = "INSERT INTO reviews (content, rating) VALUES (:content, :rating)"; //raw sql
        try(Connection conn = sql2o.open()){ //try to open a connection
            int id = (int) conn.createQuery(sql) //make a new variable
                    .bind(review) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            review.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Review> getAll() {
            try(Connection con = sql2o.open()){
                return con.createQuery("SELECT * FROM reviews")
                        .executeAndFetch(Review.class);
            }
    }

    @Override
    public void  update(int id,String newContent, int newRating) {
        String sql = "UPDATE reviews SET content = :content, rating = :rating WHERE id = :id";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("content", newContent)
                    .addParameter("id", id)
                    .addParameter("rating", newRating)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public Review findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM reviews WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Review.class); //fetch an individual item
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from reviews WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllReviews() {
        String sql = "DELETE from reviews";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


}

