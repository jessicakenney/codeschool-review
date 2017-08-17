package dao;

import models.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;


import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Guest on 8/16/17.
 */
public class Sql2oReviewDaoTest {

    private Sql2oReviewDao reviewDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    //helper
    public Review testReview() {
        return new Review("Great", 5, 1);
    }

    @Test
    public void newReviewCanBeFoundById() throws Exception {
         Review newReview = testReview();
        reviewDao.add(newReview);
        Review foundReview = reviewDao.findById(newReview.getId());
        assertEquals(newReview, foundReview);
    }

    @Test
    public void sizeOfTheDaoIsCorrect_True() throws Exception {
        Review newReview = testReview();
        Review newReview2 = new Review ("Super",4,1);
        Review newReview3 = new Review ("eh",4,1);
        reviewDao.add(newReview);
        reviewDao.add(newReview2);
        reviewDao.add(newReview3);
        assertEquals(3, reviewDao.getAll().size());
    }

    @Test
    public void contentOfReviewReturned_True() throws Exception {
        Review newReview = testReview();
        reviewDao.add(newReview);
        assertEquals("Great", reviewDao.findById(1).getContent());
    }

    @Test
    public void updateContentOfReviewReturned_True() throws Exception {
        Review newReview = testReview();
        reviewDao.add(newReview);
        reviewDao.update(1, "great",5, 1);
        assertEquals("great", reviewDao.findById(1).getContent());
    }

    @Test
    public void sizeOfTheDaoIsCorrectAfterDelete_True() throws Exception {
        Review newReview = testReview();
        Review newReview2 = new Review ("blah",4,1);
        Review newReview3 = new Review ("amazin",4,1);
        reviewDao.add(newReview);
        reviewDao.add(newReview2);
        reviewDao.add(newReview3);
        reviewDao.deleteById(2);
        assertEquals(2, reviewDao.getAll().size());
    }

    @Test
    public void sizeOfTheDaoIsCorrectAfterDeleteAll_True() throws Exception {
        Review newReview = testReview();
        Review newReview2 = new Review ("blah",4,1);
        Review newReview3 = new Review ("thumbs up",4,1);
        reviewDao.add(newReview);
        reviewDao.add(newReview2);
        reviewDao.add(newReview3);
        reviewDao.clearAllReviews();
        assertEquals(0, reviewDao.getAll().size());
    }
    @Test
    public void getReviewByCodeSchoolIdReturnsCorrectly_True() throws Exception {
        Review newReview =testReview();
        Review newReview2 = new Review ("blah",4,1);
        reviewDao.add(newReview);
        reviewDao.add(newReview2);
        List<Review> testReviews = reviewDao.findByCodeSchoolId(1);
        assertEquals("Great",testReviews.get(0).getContent());
        assertEquals(2,testReviews.size());
    }

    @Test
    public void getReviewByCodeSchoolIdReturnsCorrectlyWhenEmpty_True() throws Exception {
        List<Review> testReviews = reviewDao.findByCodeSchoolId(1);
        assertEquals(0,testReviews.size());
    }

}

