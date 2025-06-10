<template>
    <button class="btn" :class="buttonClass" :disabled="disabled" @click="$emit('click')" type="submit">
        <slot />
    </button>
</template>

<script setup>
defineProps({
    type: { type: String, default: 'primary' },
    disabled: { type: Boolean, default: false }
})

const buttonClass = computed(() => {
    return {
        'btn-primary': props.type === 'primary',
        'btn-secondary': props.type === 'secondary',
        'btn-danger': props.type === 'danger'
    }
})
</script>
