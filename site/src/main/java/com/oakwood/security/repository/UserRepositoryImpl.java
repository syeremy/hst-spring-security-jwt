package com.oakwood.security.repository;

import com.oakwood.security.model.repo.User;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * [description]
 *
 * @author syeremy
 * @version 1.0
 * @date 3/2/17
 **/

@Repository
public class UserRepositoryImpl implements UserRepository {

    static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private String queryLanguage = "xpath";
    private String userQuery = "//hippo:configuration/hippo:users/{0}";

    private javax.jcr.Repository systemRepository;
    private Credentials systemCreds;

    @Override
    public User findByUsername(String username) {

        User user = new User();
        Session session = null;

        try {
            if(this.getSystemCredentials() != null) {
                session = this.getSystemRepository().login(this.getSystemCredentials());
            } else {
                session = this.getSystemRepository().login();
            }

            String e = MessageFormat.format(userQuery, new Object[]{username});
            if(log.isDebugEnabled()) {
                log.debug("Searching user with query: " + e);
            }

            Query q = session.getWorkspace().getQueryManager().createQuery(e, queryLanguage);
            QueryResult result = q.execute();
            NodeIterator nodeIt = result.getNodes();
            Node userNode = nodeIt.hasNext()?nodeIt.nextNode():null;


            String passwordProp = userNode.getProperty("hipposys:password").getString();
            boolean enabled = userNode.getProperty("hipposys:active").getBoolean();
            String passwordlastmodified = getProperty(userNode, "passwordlastmodified");

            user.setUsername(username);
            user.setFirstname(getProperty(userNode, "firstname"));
            user.setLastname(getProperty(userNode, "lastname"));
            user.setEmail(getProperty(userNode, "email"));
            user.setEnabled(enabled);
            user.setPassword(passwordProp);

            if(passwordlastmodified  != null) {
                //java.text.ParseException: Unparseable date: "2017-03-06T08:38:44.396-05:00"
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
                Date date = format.parse(passwordlastmodified);
                user.setLastPasswordResetDate(date);
            }


        }
        catch (ParseException ex) {
            log.warn("Failed to load user.", ex);
        }
        catch (RepositoryException ex) {
            log.warn("Failed to load user.", ex);
        }
        finally {
            if(session != null) {
                try {
                    session.logout();
                } catch (Exception var23) {
                    ;
                }
            }

        }

        return  user;

    }

    private String getProperty(Node node, String propertyName)
    {
        String property = String.format("hipposys:%s",propertyName);
        try {
            return node.getProperty(property) != null  ? node.getProperty(property).getString() : "";
        } catch (RepositoryException e) {
           log.error(String.format("Error getting '%s' Property from User Node ", propertyName) ,e);
        }
        return  null;
    }


    public javax.jcr.Repository getSystemRepository() {
        if(this.systemRepository == null) {
            this.systemRepository = (javax.jcr.Repository) HstServices.getComponentManager().getComponent(javax.jcr.Repository.class.getName());
        }

        return this.systemRepository;
    }


    public Credentials getSystemCredentials() {
        if(this.systemCreds == null) {
            this.systemCreds = (Credentials)HstServices.getComponentManager().getComponent(Credentials.class.getName() + ".hstconfigreader");
        }

        return this.systemCreds;
    }
}
