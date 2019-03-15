package pl.asprojects.fileshare;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
//@RequestMapping("/download.do")
public class FileDownloadController {



    /**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Path of the file to be downloaded, relative to application's directory
     */
    private String filePath = "/zefit3-userguide.pdf";
    /**
     * Method for handling file download request from client
     */

    private User currentUser;


    @RequestMapping(value = "/download.do", method = RequestMethod.GET)
    public void doDownload(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {

        // get absolute path of the application
        ServletContext context = request.getServletContext();
        //String appPath = context.getRealPath("");
        //String appPath = "D:\\upload\\";
        String appPath = "C:\\Users\\Adam\\Documents\\";
        System.out.println("appPath = " + appPath);

        // construct the complete absolute path of the file
        String fullPath = appPath + filePath;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }

    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("filename") String filename,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload first!!!");
            //return "redirect:uploadStatus";
            return "redirect:upload";
        }

        try {

            // Get the file and save it on hd
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D:\\upload\\" + file.getOriginalFilename());
            int i=1;
            while(Files.exists(path)){
                path = Paths.get("D:\\upload\\["+ i +"]" + file.getOriginalFilename());
                i++;
            }
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

            FileData fileData = new FileData();
            if(filename.length()==0){file.getOriginalFilename();}
            fileData.setFilename(filename);
            fileData.setPath(path.toString());
            fileData.setAuthorID(currentUser.getId());

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "File could not be uploaded");
        }

        return "redirect:/uploadStatus";
    }

    @RequestMapping(value = "/uploadStatus")
    public String uploadStatus(){
        return "uploadStatus";
    }


    @RequestMapping(value = "/download")
    public String downloadLinkPage(){
        return "fileDLoadPage";
    }

    @RequestMapping(value = "/upload")
    public String uploadPage() {
        return "fileULoadPage";
    }
}

