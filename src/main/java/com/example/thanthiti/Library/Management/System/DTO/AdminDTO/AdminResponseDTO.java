package com.example.thanthiti.Library.Management.System.DTO.AdminDTO;

public class AdminResponseDTO {

        //    id ,name,email,password,role,createAt,update,delete
        private Long id;
        private String name;
        private String email;
        private String role;
        private String createAt;
        private String updateAt;
        private String deleteAt;

        public AdminResponseDTO() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }

        public String getDeleteAt() {
            return deleteAt;
        }

        public void setDeleteAt(String deleteAt) {
            this.deleteAt = deleteAt;
        }

}
