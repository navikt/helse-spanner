let mode = import.meta?.env?.MODE;
export const Environment = {
    isDevelopment: (mode && (mode === 'development')) ?? true
}
