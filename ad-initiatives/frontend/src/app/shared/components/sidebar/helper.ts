export interface INavbarData {
    accesslevel: string;
    routeLink: string;
    icon?: string;
    label: string;
    expanded?: boolean;
    items?: INavbarData[];
}