type Result = {
    code: number,
    msg: string,
    data: any
}

type Category = {
    id: number,
    name: string,
}

type ContestProfile = {
    id: number,
    name: string,
    topic: string,
    year: string,
    starting_time: string,
    registration_deadline: string,
    result_announcement_time: string,
}

type ContestDetails = {
    id: number,
    name: string,
    year: string,
    topic: string,
    intro: string,
    starting_time: string,
    registration_deadline: string,
    result_announcement_time: string,
    status: number
}

declare type UserDetail = {
    nickname: string
    uid: number
    username: string
}

type UserTeamOfContest = {
    created?: ContestTeamWork
    joined?: ContestTeamWork[]
}

type UserProfile = {
    uid: number,
    nickname: string
}

type TeamFullInfo = {
    id: number,
    name: string,
    captain: UserProfile,
    member: UserProfile[]
}

type WorkFullInfo = {
    id: number,
    title: string,
    intro: string,
    likes: number,
    award: number,
    awardTime: string,
    imgs: string[],
}

type ContestTitle = {
    id: number,
    name: string,
}

type ContestTeamWork = {
    contest?: ContestTitle,
    team?: TeamFullInfo,
    work?: WorkFullInfo
}

