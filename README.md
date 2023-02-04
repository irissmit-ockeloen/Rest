## Firebase

### Generate a private key file for your service account

1. In the Firebase console, open [Settings](https://console.firebase.google.com/project/_/settings/serviceaccounts/adminsdk) > Service Accounts.
2. Click Generate New Private Key, then confirm by clicking Generate Key.
3. Securely store the JSON file containing the key
4. Set the environment variable GOOGLE_APPLICATION_CREDENTIALS to the file path of the JSON file that contains your service account key.
