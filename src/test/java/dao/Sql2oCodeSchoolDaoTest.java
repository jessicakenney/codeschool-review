package dao;

import models.CodeSchool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;



import static org.junit.Assert.*;

/**
 * Created by Guest on 8/16/17.
 */
public class Sql2oCodeSchoolDaoTest {

    private Sql2oCodeSchoolDao codeSchoolDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        codeSchoolDao = new Sql2oCodeSchoolDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    //helper
    public  CodeSchool testCodeSchool (String name){
        CodeSchool codeSchool = new CodeSchool(name);
        return codeSchool;
    }

    @Test
    public void newCodeSchoolCanBeFoundById() throws Exception {
        CodeSchool newCodeSchool = testCodeSchool("Epicodus");
        codeSchoolDao.add(newCodeSchool);
        CodeSchool foundCodeSchool = codeSchoolDao.findById(newCodeSchool.getId());
        assertEquals(newCodeSchool, foundCodeSchool);
    }
    @Test
    public void sizeOfTheDaoIsCorrect_True() throws Exception {
        CodeSchool newCodeSchool = testCodeSchool("Epicodus");
        CodeSchool newCodeSchool2 = testCodeSchool("Hack Reactor");
        CodeSchool newCodeSchool3 = testCodeSchool("Code Fellows");
        codeSchoolDao.add(newCodeSchool);
        codeSchoolDao.add(newCodeSchool2);
        codeSchoolDao.add(newCodeSchool3);
        assertEquals(3, codeSchoolDao.getAll().size());
    }
    @Test
    public void nameOfCodeSchoolReturned_True() throws Exception {
        CodeSchool newCodeSchool = testCodeSchool("Epicodus");
        codeSchoolDao.add(newCodeSchool);
        assertEquals("Epicodus", codeSchoolDao.findById(1).getName() );
    }
    @Test
    public void updateNameOfCodeSchoolReturned_True() throws Exception {
        CodeSchool newCodeSchool = testCodeSchool("Epicodus");
        codeSchoolDao.add(newCodeSchool);
        codeSchoolDao.update(1,"EPICODUS");
        assertEquals("EPICODUS", codeSchoolDao.findById(1).getName() );
    }
    @Test
    public void sizeOfTheDaoIsCorrectAfterDelete_True() throws Exception {
        CodeSchool newCodeSchool = testCodeSchool("Epicodus");
        CodeSchool newCodeSchool2 = testCodeSchool("Hack Reactor");
        CodeSchool newCodeSchool3 = testCodeSchool("Code Fellows");
        codeSchoolDao.add(newCodeSchool);
        codeSchoolDao.add(newCodeSchool2);
        codeSchoolDao.add(newCodeSchool3);
        codeSchoolDao.deleteById(2);
        assertEquals(2, codeSchoolDao.getAll().size());
    }

    @Test
    public void sizeOfTheDaoIsCorrectAfterDeleteAll_True() throws Exception {
        CodeSchool newCodeSchool = testCodeSchool("Epicodus");
        CodeSchool newCodeSchool2 = testCodeSchool("Hack Reactor");
        CodeSchool newCodeSchool3 = testCodeSchool("Code Fellows");
        codeSchoolDao.add(newCodeSchool);
        codeSchoolDao.add(newCodeSchool2);
        codeSchoolDao.add(newCodeSchool3);
        codeSchoolDao.clearAllCodeSchools();
        assertEquals(0, codeSchoolDao.getAll().size());
    }





}