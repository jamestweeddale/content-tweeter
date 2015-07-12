import com.tweeddale.contenttweeter.ContentTweeter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Created by James on 7/4/2015.
 */
public class TweetPony {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    }
}
