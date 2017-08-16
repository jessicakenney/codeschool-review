package dao;
import java.util.List;
import models.CodeSchool;

/**
 * Created by Guest on 8/16/17.
 */
public interface CodeSchoolDao {

    //create
    void add (CodeSchool codeSchool);

    //read
    List<CodeSchool> getAll();

    CodeSchool findById(int id);

    //update
    void update(int id, String content);

    //delete
    void deleteById(int id);

    void clearAllCodeSchools();

}
