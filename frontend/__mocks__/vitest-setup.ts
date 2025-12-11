import { vi } from 'vitest'

// Mock environment
vi.mock('./src/external/environment', () => ({
    Environment: {
        isDevelopment: true,
    },
}))

// Mock react-router-dom
vi.mock('react-router-dom', () => ({
    useNavigate: () => vi.fn(),
    useLocation: () => ({ pathname: '/' }),
    useParams: () => ({}),
    useSearchParams: () => [new URLSearchParams(), vi.fn()],
    BrowserRouter: ({ children }: any) => children,
    MemoryRouter: ({ children }: any) => children,
    Route: ({ children }: any) => children,
    Routes: ({ children }: any) => children,
    Link: ({ children, to }: any) => children,
    Navigate: () => null,
    Outlet: () => null,
}))

