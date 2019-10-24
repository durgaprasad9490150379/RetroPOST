package com.example.retrofitpost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostData {

        @SerializedName("FirstName")
        @Expose
        private String FirstName;

        @SerializedName("LastName")
        @Expose
        private String LastName;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("phone")
        @Expose
        private int phone;

        @SerializedName("Address")
        @Expose
        private String Address;

        @SerializedName("organization")
        @Expose
        private String organization;

        @SerializedName("blacklisted")
        @Expose
        private boolean blacklisted;



        public PostData(String f_name, String l_name, String email, int phone, String address, String organization, boolean bl) {
            this.FirstName = f_name;
            this.LastName = l_name;
            this.email = email;
            this.phone = phone;
            this.Address = address;
            this.organization = organization;
            this.blacklisted = bl;
        }


        public String getFirstName() {
            return FirstName;
        }

        public String getLastName() {
            return LastName;
        }

        public String getEmail() {
            return email;
        }

        public int getPhone() {
            return phone;
        }

        public String getAddress() {
            return Address;
        }

        public String getOrganization() {
            return organization;
        }

        public boolean getBlacklisted() {
            return blacklisted;
        }

}
