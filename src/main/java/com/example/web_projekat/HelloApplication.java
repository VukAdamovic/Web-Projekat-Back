package com.example.web_projekat;

import com.example.web_projekat.repositories.category.CategoryRepository;
import com.example.web_projekat.repositories.category.MySqlCategoryRepository;
import com.example.web_projekat.repositories.comment.CommentRepository;
import com.example.web_projekat.repositories.comment.MySqlCommentRepository;
import com.example.web_projekat.repositories.news.MySqlNewsRepository;
import com.example.web_projekat.repositories.news.NewsRepository;
import com.example.web_projekat.repositories.news_tags.MySqlNewsTagsRepository;
import com.example.web_projekat.repositories.news_tags.NewsTagsRepository;
import com.example.web_projekat.repositories.role.MySqlRoleRepository;
import com.example.web_projekat.repositories.role.RoleRepository;
import com.example.web_projekat.repositories.tags.MySqlTagsRepository;
import com.example.web_projekat.repositories.tags.TagsRepository;
import com.example.web_projekat.repositories.user.MySqlUserRepository;
import com.example.web_projekat.repositories.user.UserRepository;
import com.example.web_projekat.services.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {


    public HelloApplication(){
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlRoleRepository.class).to(RoleRepository.class).in(Singleton.class);

                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);

                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);

                this.bind(MySqlTagsRepository.class).to(TagsRepository.class).in(Singleton.class);

                this.bind(MySqlNewsRepository.class).to(NewsRepository.class).in(Singleton.class);

                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                this.bind(MySqlNewsTagsRepository.class).to(NewsTagsRepository.class).in(Singleton.class);

                this.bindAsContract(RoleService.class);

                this.bindAsContract(UserService.class);

                this.bindAsContract(CategoryService.class);

                this.bindAsContract(TagsService.class);

                this.bindAsContract(NewsService.class);

                this.bindAsContract(CommentService.class);

                this.bindAsContract(NewsTagsService.class);
            }
        };
        register(binder);
        register(CORSFilter.class);

        // Ucitavamo resurse
        packages("com.example.web_projekat");
    }

}