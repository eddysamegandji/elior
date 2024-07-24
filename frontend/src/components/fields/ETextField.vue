<script setup lang="ts">

export interface Props {
    modelValue?: string | number
    label?: string
    errorMessage?: string
    inline?: boolean
}
const props = defineProps<Props>()
const emit = defineEmits(['update:modelValue'])

</script>

<template>
    <div>
        <v-text-field :model-value="modelValue" :label="label" :error-message="props.errorMessage" outlined
            :error="!!props.errorMessage" @update:model-value="emit('update:modelValue', $event)" />

    </div>
</template>