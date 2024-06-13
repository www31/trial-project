import { SelectItem } from "primeng/api";

export interface AddUserInterface  {
    label: string;
    placeholder: string;
    type: string;
    element: string;
    field: string;
    data?: any;
    isRequired: boolean;
}

export interface DropdownInterface {
    name: string,
    code: string
}

export type ValidKeys = 'lastname' | 
                        'firstname' | 
                        'middlename' | 
                        'suffix' | 
                        'gender' | 
                        'emailAddress' | 
                        'careerStep' | 
                        'empId' | 
                        'region' | 
                        'role' | 
                        'team' | 
                        'status' | 
                        'skills';
export interface Certification {
    cert_name?: string;
    date_certified?: Date;
}
export type ValidKeysCertification = keyof Certification;

export interface ResourceInfosInterface {
    lastname?: string,
    firstname?: string,
    middlename?: string,
    suffix?: string,
    gender?: string,
    emailAddress?: string,
    careerStep?: string,
    empId?: string,
    region?: string,
    role?: string,
    team?: string,
    isEnabled?: boolean,
    status?: string,
    skills?: string
}

export interface ResourceInfoInterface {
    lastname: string;
    firstname: string;
    middlename: string;
    suffix: string;
    gender: string;
    emailAddress: string;
    careerStep: string;
    empId: string;
    region: string;
    role: string;
    team: string;
    status: string;
    skills: string;
    [key: string]: string;
  };


export interface SelectItemGroupInterface {
    label: string;
    value?: any;
    items?: SelectItem[];
}