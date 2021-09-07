import {Backend} from "./backend";
import {restBackend} from "./restBackend";

export type Externals = {
    backend: Backend
}

export let externals = {
    backend: restBackend
}