type Session = {
    sessionID: number;
    isFilmSession: boolean;
    options: Option[];
    isActive: boolean;
}

type Option =
    {
        optionID: number;
        description: string;
        voteTally: number;
    }


type Movie = {
    overview: string;
    release_date: string;
    vote_average: string;
    title: string;
    poster_path: string;
}







export { Session, Option, Movie };