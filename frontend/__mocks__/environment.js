jest.mock('../src/external/environment', () => ({
    Environment: {
        isDevelopment: true,
    },
}))
