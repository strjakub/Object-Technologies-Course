package com.example.backend;

import com.example.backend.dao.ImageDAO;
import com.example.backend.dao.ThumbnailDAO;
import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.service.SessionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        ImageDAO dao = new ImageDAO();
        ThumbnailDAO dao2 = new ThumbnailDAO();
        System.out.println(dao.create(null, "jpg"));
        Image image = SessionService.getSession().createQuery("SELECT i FROM Image i", Image.class).getSingleResult();
        System.out.println("\n\n" + image);
        System.out.println(dao2.create(1, null, "jpg", image));
        Thumbnail thumbnail = SessionService.getSession().createQuery("SELECT t FROM Thumbnail t", Thumbnail.class).getSingleResult();
        System.out.println("\n\n" + thumbnail);
    }

}
