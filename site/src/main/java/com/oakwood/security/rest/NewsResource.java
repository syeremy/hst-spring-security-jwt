package com.oakwood.security.rest;

import com.oakwood.security.rest.model.NewsModel;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.modelmapper.ModelMapper;
import org.onehippo.forge.security.support.springsecurity.demo.beans.NewsDocument;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [description]
 *
 * @author syeremy
 * @version 1.0
 * @date 3/3/17
 **/

@RestController
public class NewsResource  {

    @GET
    @Path("news")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<NewsModel> getAuthenticatedUser(@Context HttpServletRequest servletRequest,
                                                @Context HttpServletResponse servletResponse) {

        HstRequestContext context = RequestContextProvider.get();
        List<NewsDocument>  result = null;
        try {
            result = this.getNews(context, NewsDocument.class);
        } catch (QueryException e) {
            e.printStackTrace();
        }

        ModelMapper modelMapper = new ModelMapper();
        List<NewsModel> news = result.stream().map(item -> modelMapper.map(item, NewsModel.class)).collect(Collectors.toList());



        return  news;
    }


    private <T, S extends HippoDocument>  List<T> getNews(HstRequestContext context, Class<S> source)
            throws QueryException {

        List<T> entities = new ArrayList<>();

        HstQuery query = context.getQueryManager().createQuery(context.getSiteContentBaseBean(), source, true);
        HstQueryResult result = query.execute();


        HippoBeanIterator iterator = result.getHippoBeans();
        while(iterator.hasNext())
        {
            entities.add( (T)iterator.next());
        }


        return entities;
    }

    protected void init()
    {

    }
}
