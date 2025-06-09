import api from './api';

export default function createCrudService(resource) {
    return {
        getAll() {
            return api.get(`/admin/${resource}`);
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
