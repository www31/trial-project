// export const empStatusInputs = [
//     { name: 'Active', code: 'ac' },
//     { name: 'Inactive', code: 'iac' },
// ];
export const empStatusInputs = [
    'Active',
    'Inactive'
];

// export const gender = [
//     { name: 'Male', code: 'm' },
//     { name: 'Female', code: 'f' },
//     { name: 'Other', code: 'o' },
// ];

export const gender = [
    'Male',
    'Female',
    'Other'
];

export const personalInfoInputs = [
    {label: 'Last Name', field: "lastname", placeholder: 'Last Name', type: 'text', element: 'input', data: null, isRequired: true},
    {label: 'First Name', field: "firstname", placeholder: 'First Name', type: 'text', element: 'input', data: null, isRequired: true},
    {label: 'Middle Name', field: "middlename", placeholder: 'Middle Name', type: 'text', element: 'input', data: null, isRequired: false},
    {label: 'Suffix', field: "suffix", placeholder: 'Suffix', type: 'text', element: 'input', data: null, isRequired: false},
    {label: 'Gender', field: "gender", placeholder: 'Gender', type: 'text', element: 'singleselect-dropdown', data: gender, isRequired: false},
    {label: 'Email Address', field: "emailAddress", placeholder: 'Email Address', type: 'text', element: 'input', data: null, isRequired: true},
    {label: 'Career Step', field: "careerStep", placeholder: 'Career Step', type: 'text', element: 'input', data: null, isRequired: true},
    {label: 'Employee Id', field: "empId", placeholder: 'Employee Id', type: 'text', element: 'input', data: null, isRequired: true},
    {label: 'Region', field: "region", placeholder: 'Region', type: 'text', element: 'input', data: null, isRequired: false},
    {label: 'Role/s', field: "role", placeholder: 'Role/s', type: 'text', element: 'input', data: null, isRequired: true},
    {label: 'Team/s', field: "team", placeholder: 'Team/s', type: 'text', element: 'multiselect-dropdown', data: null, isRequired: true},
    {label: 'Employee Status', field: "status", placeholder: 'Employee Status', type: 'text', element: 'singleselect-dropdown', data: empStatusInputs, isRequired: true},
];

export const techStacksInputs = [
    {label: 'Skills', field: "skills", placeholder: 'Skills', type: 'text', element: 'multiselect-dropdown', data: null, isRequired: false},

];

export const certificationInputs = [
    {label: 'Name', field: "cert_name", placeholder: 'Certification Name', type: 'text', element: 'input', data: null, isRequired: false},
    {label: 'Date Certified', field: "date_certified", placeholder: 'Date Certified', type: 'text', element: 'calendar', data: null, isRequired: false},
    // {label: 'Certificate Document', field: "certificate_doc", placeholder: 'File to be upload', type: 'file', element: 'input'}
];