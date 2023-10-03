# InstallReferrer

Add these two libraries.


    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.android.installreferrer:installreferrer:2.2'

Add InstallApi.java to your project

Add these lines in MainActivity or Splash oncreate

        InstallApi installApi = new InstallApi("Your user id, token etc");
        installApi.callApi(this);
