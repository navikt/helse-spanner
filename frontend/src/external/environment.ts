const viteMode = import.meta?.env?.MODE;
const nodeMode = process?.env?.NODE_ENV
export const Environment = {
    isDevelopment:
        ((viteMode && (viteMode === 'development')) ||
        (nodeMode && (nodeMode === 'test')))
        ?? false
}
