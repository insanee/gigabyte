/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte;

import by.insane.gigabyte.orm.Images;
import by.insane.gigabyte.orm.Product;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Андрій
 */
public class FTPLoader {

    private String server;
    private int port;
    private String user;
    private String password;

    public FTPLoader(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public FTPLoader() {
        server = "ftp.gigabyteshop.hol.es";
        port = 21;
        user = "u734926935.insane";
        password = "111111";
    }

    public boolean uploadToFtp(File file, String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        boolean done = false;
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            InputStream inputStream = new FileInputStream(file);
            done = ftpClient.storeFile(fileName, inputStream);
            inputStream.close();

        } finally {

            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }

        }
        return done;
    }

    public void uploadToFtp() {

    }

    public void dropImages(Product product) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();

            for (FTPFile file : ftpClient.listFiles()) {
                for (Images image : product.getImages()) {
                    if (file.getName().equals(image.getName())) {
                        ftpClient.deleteFile(file.getName());
                        break;
                    }
                }
            }

        } finally {

            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }

        }
    }

    public void dropImage(Images image) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();

            ftpClient.deleteFile(image.getName());

        } finally {

            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }

        }
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
