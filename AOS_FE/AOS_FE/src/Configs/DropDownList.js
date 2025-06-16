import api from "./api";

async function dropDownVariant() {
    const VariantValues = await api.get('/VariantValues')
    return VariantValues.data
}

async function dropDown(name) {
    const products = await api.get('/' + name)
    return products.data;
}

export { dropDownVariant, dropDown }