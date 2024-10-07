package lk.ijse.notecollector.config;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "lk.ijse.notecollector")
@EnableWebMvc
/* Controller class ekata adalawa danna one configurations nisai methenta damme. */
/* meka dammama ape resources over use wenna puluwan */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // primery memory eken handle karanna one uparima MB pramanaya. ita wada wadi unoth handle wenne secondary memory eken.
        maxFileSize = 1024 * 1024 * 5, //5Mb here
        maxRequestSize = 1024 * 1024 * 10 //10Mb here (file size + other data size)
)
public class WebAppConfig {
}
