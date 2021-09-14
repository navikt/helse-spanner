export const mapNotUndefined = <T, Y>(array: T[], mapping: (kontekst: T, index: number) => Y | undefined): Y[] => {
    return array
        .map((element, index): [T, number] => ([element, index]))
        .map(([element, index]) => mapping(element, index))
        .filter(y => y != undefined) as Y[]
}