import eslintPluginPrettierRecommended from 'eslint-plugin-prettier/recommended'
import reactHooks from 'eslint-plugin-react-hooks'
import { defineConfig } from 'eslint/config'
import importPlugin from 'eslint-plugin-import'
import typescriptEslint from 'typescript-eslint'

const eslintConfig = defineConfig([
    reactHooks.configs.flat['recommended-latest'],
    {
        extends: [
            eslintPluginPrettierRecommended,
            importPlugin.flatConfigs.recommended,
            importPlugin.flatConfigs.typescript,
            ...typescriptEslint.configs.recommended,
        ],
        rules: {
            'prettier/prettier': 'warn',
            'import/order': 'off',
            'import/no-extraneous-dependencies': 'error',
            '@typescript-eslint/no-namespace': 'off',
            'react-hooks/refs': 'off',
            '@typescript-eslint/explicit-function-return-type': 'off',
            'import/no-unresolved': ['error', { ignore: ['^eslint/config$', '^typescript-eslint$'] }],
        },
    },
    {
        files: ['**/*.ts', '**/*.tsx'],
    },
])

export default eslintConfig
