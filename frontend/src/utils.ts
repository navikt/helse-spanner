export const mapNotUndefined = <T, Y>(array: T[], mapping: (kontekst: T, index: number) => Y | undefined): Y[] => {
    return array
        .map((element, index): Y | undefined => mapping(element, index))
        .filter(y => y != undefined) as Y[]
}

//https://stackoverflow.com/questions/2559318/how-to-check-for-an-undefined-or-null-variable-in-javascript
export const hasValue = (it: any) =>
     it != null

export const writeToClipboard = (data: string) =>
    navigator.clipboard.writeText(data)
        .catch(
            (error) => console.warn('Error copying to clipboard:', error)
        )
