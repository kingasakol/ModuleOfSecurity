package initializationModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safety.library.SQLModule.Builder;
import org.safety.library.SQLModule.DerbyQueryBuilder;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.Role;

import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;

public class DerbyQueryBuilderTest {

    //example sql query
    private String exampleSQL = "select testmodel0_.testModelID as testmode1_2_, testmodel0_.someValue as somevalu2_2_ from test_model testmodel0_";
    private String resultForExampleSQL = "select testmodel0_.testModelID as testmode1_2_, testmodel0_.someValue as somevalu2_2_ from test_model testmodel0_ where  testmodel0_.testModelID = 1 or  testmodel0_.testModelID = 2 or  testmodel0_.testModelID = 3 ";
    private String exampleSQLWithWhere = "select testmodel0_.testModelID as testmode1_2_, testmodel0_.someValue as somevalu2_2_ from test_model testmodel0_ where testmodel0_.someValue=?";
    private String resultForExampleSQLWithWhere = "select testmodel0_.testModelID as testmode1_2_, testmodel0_.someValue as somevalu2_2_ from test_model testmodel0_  where  testmodel0_.testModelID = 1 or  testmodel0_.testModelID = 2 or  testmodel0_.testModelID = 3 or  testmodel0_.someValue=?";
    private List<AccessListRow> accessListRowList;

    @BeforeEach
    public void prepareTest(){
        accessListRowList = new LinkedList<>();
        accessListRowList.add(new AccessListRow(new Role("admin"), 1, "TestModel", true, true, true));
        accessListRowList.add(new AccessListRow(new Role("admin"), 2, "TestModel", true, true, true));
        accessListRowList.add(new AccessListRow(new Role("admin"), 3, "TestModel", true, true, true));
    }

    @Test
    public void constructsValidSQLQuery() throws Exception {
        Builder builder = new DerbyQueryBuilder();
        assertEquals(builder.returnPreparedSQL(accessListRowList, exampleSQL, ""), resultForExampleSQL);
    }

    @Test
    public void constructValidSQLQueryWithWhere() throws Exception{
        Builder builder = new DerbyQueryBuilder();
        assertEquals(builder.returnPreparedSQL(accessListRowList, exampleSQLWithWhere, ""), resultForExampleSQLWithWhere);
    }
}
