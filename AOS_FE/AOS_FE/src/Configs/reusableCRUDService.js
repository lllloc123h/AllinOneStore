import api from './api';

export default function createCrudService(resource) {
    return {
        async getAll(page, size, FilterObject) {
            const cleanFilter = {};
            Object.keys(FilterObject || {}).forEach(key => {
                if (FilterObject[key] !== '' && FilterObject[key] !== null && FilterObject[key] !== undefined) {
                    cleanFilter[key] = FilterObject[key];
                }
            });
            const params = {
                page: page,
                size: size,
                ...cleanFilter // Spread each key-value as query params
            };
            return api.get(`/admin/${resource}`, { params });
        },

        getById(id) {
            return api.get(`/admin/${resource}/${id}`);
        },

        create(data) {
            return api.post(`/admin/${resource}`, data);
        },

        update(id, data) {
            console.log(`Updating ${resource} with ID ${id}`, { ...data });
            return api.put(`/admin/${resource}/${id}`, { ...data });
        },

        delete(id) {
            return api.delete(`/admin/${resource}/${id}`);
        }
    };
}
