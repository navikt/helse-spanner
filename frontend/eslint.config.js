const reactPlugin = require('eslint-plugin-react')
const eslint = require('@eslint/js')
const tseslint = require('typescript-eslint')

module.exports = [
    reactPlugin.configs.flat.recommended,
    eslint.configs.recommended,
    ...tseslint.configs.recommended,
    {
        files: ['**/*.ts', '**/*.tsx'],
    },
    {
        settings: {
            react: {
                version: 'detect',
            },
        },
    },
]
