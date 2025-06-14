import api from './api';

export default function createCrudService(resource) {
    return {
        getAll(page, size, FilterObject) {
            let query = ''
            if (FilterObject != null) {
                const keyQuery = Object.keys(FilterObject);
                query = keyQuery.map(key => `${key}=${encodeURIComponent(FilterObject[key])}`).join('&');
                console.log(query)
            }
            return api.get(`/admin/${resource}` + "?page=" + page + "&size=" + size);
        },

        getById(id) {
            return api.get(`/admin/${resource}/${id}`);
        },

        create(data) {
            return api.post(`/admin/${resource}`, data);
        },

        update(id, data) {
            return api.put(`/admin/${resource}/${id}`, data);
        },

        delete(id) {
            return api.delete(`/admin/${resource}/${id}`);
        }
    };
}
