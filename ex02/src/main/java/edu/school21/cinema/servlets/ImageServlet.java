package edu.school21.cinema.servlets;

import edu.school21.cinema.config.ApplicationConfig;
import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.AuthenticationService;
import edu.school21.cinema.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/images")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, 	// 10 MB
        maxFileSize = 1024 * 1024 * 50,      	// 50 MB
        maxRequestSize = 1024 * 1024 * 100  	// 100 MB
)
@Slf4j
public class ImageServlet extends HttpServlet {
    private ApplicationConfig applicationConfig;
    private AuthenticationService authenticationService;
    private ImageService imageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("applicationContext");
        imageService = applicationContext.getBean(ImageService.class);
        applicationConfig = applicationContext.getBean(ApplicationConfig.class);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id != null) {
            User user = (User) request.getSession().getAttribute("user");
            String path = "";

            for (Image image : user.getImages()) {
                if (String.valueOf(image.getId()).equals(id)) {
                    path = image.getPath();
                    break;
                }
            }

            request.getSession().setAttribute("path", path);
            request.getRequestDispatcher("/WEB-INF/jsp/image.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        String relativeDirPath = applicationConfig.getImgPath()  + File.separator + user.getId();
        // constructs absolute path of the directory to save uploaded file
        String uploadAbsoluteDirPath = request.getServletContext().getRealPath(relativeDirPath);
        // creates the save directory if it does not exists
        File uploadDir = new File(uploadAbsoluteDirPath);
        if (!uploadDir.exists()) {
            boolean success = uploadDir.mkdirs();
            if (!success) {
                log.error("Can't create directory");
            }
        }
        log.info("Upload File Directory = " + uploadDir.getAbsolutePath());

        List<Part> parts = request.getParts().stream()
                .filter(part -> part.getSize() > 0)
                .collect(Collectors.toList());

        //Get all the parts from request and write it to the file on server
        for (Part part : request.getParts()) {
            String fileName = getFileName(part);
            String uploadPathFile = uploadDir + File.separator + fileName;

            part.write(uploadPathFile);
            Image image = new Image(
                    part.getSubmittedFileName(),
                    relativeDirPath + File.separator + fileName,
                    part.getSize(),
                    part.getContentType(),
                    user.getId()
            );
            imageService.save(image);
            user.setImages(imageService.findImagesByUserId(user.getId()));
        }
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    /**
     * Utility method to get file name
     */
    private String getFileName(Part part) {
        return new SimpleDateFormat("HHmmssddMMyyyyy").format(new Date()) + part.getSubmittedFileName();
    }
}
