import api from "./api";

const dropDownVariant = async () => {
    api.get('/VariantValues')
}

async function dropDownBaseProducts(name) {
    api.get('/' + name)
    const hashMapById = new Map();
    products.forEach(product => {
        hashMapById.set(product.id, product);
    });
    return hashMapById;
}