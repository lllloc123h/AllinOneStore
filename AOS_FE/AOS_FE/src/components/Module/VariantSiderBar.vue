<template>
    <div v-for="(items, groupName) in mapVarriants" :key="groupName" class="filter-group">
        <h3><span class="line"></span> {{ groupName }}</h3>
        <ul class="filter-list">
            <li v-for="item in items" :key="item.id">
                <label>
                    <input type="checkbox" :value="item.signalSku" v-model="selected[groupName]" />
                    {{ item.description }}
                </label>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'


const mapVarriants = await axios.get(`http://localhost:8080/api/VariantValues`);

</script>

<style scoped>
.sidebar {
    background-color: #1c2b4a;
    color: #ffffff;
    padding: 20px;
    width: 100%;
    max-width: 300px;
    border-radius: 10px;
}

.menu-btn {
    background-color: #2e3e5c;
    color: white;
    width: 100%;
    text-align: left;
    padding: 10px 15px;
    border: none;
    border-radius: 8px;
    margin-bottom: 5px;
    position: relative;
    cursor: pointer;
}

.menu-btn:hover {
    background-color: #3f5377;
}

.arrow {
    float: right;
    transition: transform 0.3s ease;
}

.arrow.open {
    transform: rotate(180deg);
}

.ItemMenu {
    list-style-type: none;
    margin-right: 5%;
}

.list {
    background-color: #34465d;
    padding: 10px;
    margin-top: 5px;
    border-radius: 6px;

}

.Item {
    margin: 6px 0;
    list-style-type: none;
}

.link {
    color: #ffffff;
    text-decoration: none;
    transition: color 0.2s;
    padding-left: 10px;

}

.link:hover {
    color: #50b7f5;
}

#menu {
    padding: 0;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>
