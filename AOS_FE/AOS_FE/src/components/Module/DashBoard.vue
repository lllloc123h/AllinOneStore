<template>
    <div class="container-fluid vh-100">
        <div class="row h-100">

            <!-- Sidebar with 3 RouterLinks, stacked vertically -->
            <nav class="col-12 bg-dark text-white p-3 d-flex flex-column justify-content-start">
                <h3 class="mb-4">Admin Panel</h3>

                <!-- Each link in its own row -->
                <div class="mb-12">
                    <RouterLink to="/Admin/QuanLyTaiKhoan" class="nav-link text-white">
                        Accounts
                    </RouterLink>
                </div>

                <div class="mb-12">
                    <RouterLink to="/Admin/UserManagement" class="nav-link text-white">
                        User Management
                    </RouterLink>
                </div>

                <div class="mb-12">
                    <RouterLink to="/Admin/StaffManagement" class="nav-link text-white">
                        Staff Management
                    </RouterLink>
                </div>
            </nav>

            <!-- Main content area -->
            <main class="col-9 p-4 overflow-auto">
                <router-view />
            </main>

        </div>
    </div>
</template>


<script setup>
import { ref, watch, onMounted } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'

</script>
