package com.google.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class GoogleDriveStarter {

    private static final String APPLICATION_NAME = "GOOGLE DRIVE API STARTER";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
    * @author halfdev
    * @since 2019-11-26
    * Global instance of the scopes required by this APP
    * If modifying these scopes, delete your previously saved tokens/ folder.
    */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
    * @author halfdev
    * @since 2019-11-26
    * Creates an authorized Credential object.
    */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        // Load Client secrets
        InputStream in = GoogleDriveStarter.class.getResourceAsStream(CREDENTIALS_FILE_PATH);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets googleClientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // TODO Build flow and trigger user authorization request.
        // Remove Return null
        return  null;
    }
}
