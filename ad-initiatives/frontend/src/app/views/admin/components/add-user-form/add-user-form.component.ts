import { gender } from './../../../../shared/constants/add-user-form.constants';
import { CommonModule, DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output, OnInit, ViewEncapsulation, SimpleChange, SimpleChanges } from '@angular/core';
import { AddUserInterface, Certification, DropdownInterface, ResourceInfoInterface, ResourceInfosInterface, SelectItemGroupInterface, ValidKeys } from './add-user-from.interface';
import { certificationInputs, empStatusInputs, personalInfoInputs, techStacksInputs } from '../../../../shared/constants/add-user-form.constants';
import { CustomBottonComponent } from '../../../../shared/components/custom-button/custom-button.component';
import { MultiSelectModule } from 'primeng/multiselect';
import { SelectItemGroup } from 'primeng/api';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownComponent } from '../../../../shared/components/dropdown/dropdown.component';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarComponent } from '../../../../shared/components/calendar/calendar.component';
import { AddResourceService } from '../../../../service/add-resource.service';
import { DialogBoxComponent } from '../../../../shared/components/dialog-box/dialog-box.component';

@Component({
  selector: 'app-add-user-form',
  standalone: true,
  imports: [
    CommonModule, 
    CustomBottonComponent, 
    MultiSelectModule, 
    FormsModule,
    DropdownComponent,
    DropdownModule,
    CalendarComponent,
    ReactiveFormsModule,
    DialogBoxComponent
    ],
  templateUrl: './add-user-form.component.html',
  styleUrl: './add-user-form.component.css',
  encapsulation:ViewEncapsulation.None
})

export class AddUserFormComponent {
  @Input() isOpen: boolean = false;
  @Input() resource: any;
  @Input() resourceCertifications: any;
  @Input() action: string = '';
  @Output() onCloseClick = new EventEmitter<void>();
  @Output() isOpenChange = new EventEmitter<boolean>();

  public personalInfoInputs:AddUserInterface[] = personalInfoInputs;
  public techStackInfoInputs:AddUserInterface[] = techStacksInputs;
  public certificationInfoInputs:AddUserInterface[] = certificationInputs;
  public cetificationInput =  {label: 'Name', field: "cert_name", placeholder: 'Certification Name', type: 'text', element: 'input'};
  public dateCertifiedInput = {label: 'Date Certified', field: "date_certified", placeholder: 'Date Certified', type: 'text', element: 'calendar'};
  public certificationDocInput = {label: 'Certificate Document', field: "certificate_doc", placeholder: 'File to be upload', type: 'file', element: 'input'};
  teamList!: SelectItemGroupInterface[];
  skillList!: SelectItemGroupInterface[];
  selectedSkillOption!: DropdownInterface[];
  selectedTeamOption!: DropdownInterface[];
  genderOption!: any;
  statusOption!: any;
  dateSelected!: Date;
  empStatusInputs!: any;
  textInputs: string[] = [];
  public certificationInfos: Certification[] = []; 
  currentCertification: { cert_name?: string; date?: Date } = {};
  uploadForm!: FormGroup;
  
  public initialResourceInfos = {
    lastname: '',
    firstname: '',
    middlename: '',
    suffix: '',
    gender: '',
    emailAddress: '',
    careerStep: '',
    empId: '',
    region: '',
    role: '',
    team: '',
    isEnabled: null,
    status: '',
    skills:''
  }

  public resourceInfos: any = {
    lastname: '',
    firstname: '',
    middlename: '',
    suffix: '',
    gender: '',
    emailAddress: '',
    careerStep: '',
    empId: '',
    region: '',
    role: '',
    team: '',
    isEnabled: null,
    status: '',
    skills:''
  }

  ngOnInit(): void { 
    this.getSkills();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['resource']) {
      const defaultValues = {
        lastname: '',
        firstname: '',
        middlename: '',
        suffix: '',
        gender: '',
        emailAddress: '',
        careerStep: '',
        empId: '',
        region: '',
        role: '',
        team: '',
        isEnabled: null,
        status: '',
        skills: ''
      };

      this.resourceInfos = {
        ...defaultValues,
        ...changes['resource'].currentValue?.data
      };

      this.genderOption = this.resourceInfos.gender;
      this.statusOption = this.resourceInfos.isEnabled === true ? 'Active' : this.resourceInfos.isEnabled === null ? '' :  'Inactive'
      this.resourceInfos.status = this.resourceInfos.isEnabled === true ? 'Active' : this.resourceInfos.isEnabled === null ? '' :  'Inactive'
      this.selectedTeamOption = this.resourceInfos.team ? this.resourceInfos.team.split(',') : []
      this.selectedSkillOption = this.resourceInfos.skills ? this.resourceInfos.skills.split(',') : []
      console.log(this.resourceInfos, "<<<< initial value")
    }

    if(changes['resourceCertifications']) {
        this.items.clear()
        const certificationData = changes['resourceCertifications'].currentValue?.data;
        if (certificationData && Array.isArray(certificationData)) {
          // Populate the FormArray with new data
          certificationData.forEach((certification) => {
            const newDate = new Date(certification.certificationDate)

            const fileBlob = new Blob([certification.fileContent]);
            const file = new File([fileBlob], certification.fileName);

            const itemGroup = this.fb.group({
              name: [certification.certificationName || ''],
              calendar: [newDate || null], 
              fileupload: [fileBlob || null],
              id:certification.id,
            });
  
            this.items.push(itemGroup);

            console.log(itemGroup, "<<<<<<itemGroup")
          });
        }

    }
    console.log(this.resourceCertifications, "<<<<<< resourceCertifications")
  }

  constructor(private fb: FormBuilder, private addResourceService: AddResourceService) {

      this.teamList = [
        {
            label: 'Ad Public',
            value: 'ad_public',
        },
        {
            label: 'EMPF',
            value: 'empf',
        },
        {
            label: 'Ad Initiative',
            value: 'ad_initiative'
        }
    ];

    this.skillList = [
      {
          label: '',
          value: '',
      }
  ];

    this.empStatusInputs = empStatusInputs;
  }

  form = this.fb.group({
    items:this.fb.array([]),
  });

  get items() {
    return this.form.get('items') as FormArray;
  }

  deleteItem(index: number) {
    this.items.removeAt(index);
  }

  addItem() {
    this.items.push(
      this.fb.group({
        name: [''],
        calendar: [new Date()],
        fileupload: [null]

      })
    )
  }

  onClose() {
    this.isOpen = false;
    this.onCloseClick.emit()
  }

  preventBackdropClick(event: MouseEvent) {
    event.stopPropagation();
  }

  onClosed() {
    this.isOpen = false;
    this.openDialog = false;
    this.isValidEmail = false;
    this.resetFields()
    this.isOpenChange.emit(this.isOpen)
    
  }

  getValidKey(key: string) {
    return key as ValidKeys;
  }

  onInputChange(key: string, newValue: string) {
    this.resourceInfos[this.getValidKey(key)] = newValue;
  }

  onMultiSelectChange(key: string, selected: Array<String>) {
    this.resourceInfos[this.getValidKey(key)] = selected.join(',')
  }

  onSingleSelectChange(key: string, name: string) {
    this.resourceInfos[this.getValidKey(key)] = name;
  }

  onFileChange(event: Event, index: number, items:any) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const selectedFile = input.files[0];
      const control = this.items.at(index);
      control.patchValue({ fileupload: selectedFile });
      control.get('fileupload')?.updateValueAndValidity();
    }
  }

  validateRequiredProperties(
    resourceInfo: ResourceInfoInterface,
    requiredFields: string[]
  ): string[] {
    const errors: string[] = [];
  
    requiredFields.forEach((field) => {
      if (!resourceInfo[field]) {
        // errors.push(`${field} is required`);
        errors.push(field);
      }
    });
  
    return errors;
  }

  validationErrors: string[] = [];

  checkValidation() {
    const requiredFields = ['lastname', 'firstname', 'emailAddress', 'careerStep', 'empId', 'role', 'team', 'status'];
    
    this.validationErrors = this.validateRequiredProperties(
      this.resourceInfos,
      requiredFields
    );

    if (this.validationErrors.length === 0) {
      console.log('Validation passed. All required fields are filled.');
    } else {
      console.log('Validation failed with errors:', this.validationErrors);
    }
  }

  requiredFieldDialogMsg() {
    return `Please fill out required ${this.validationErrors.length !== 0 ? this.validationErrors[0] : 'fields'}`
  }

  resetFields() {
    this.resourceInfos = this.initialResourceInfos;
    this.genderOption = '';
    this.statusOption = '';
    this.selectedTeamOption = []
    this.selectedSkillOption = []
    while (this.items.length > 0) {
      this.items.removeAt(0);
    }

    // this.onClosed();
  }

  openDialog = false;
  isValidEmail = false;
  isAddSuccess = false;
  isEditSuccess = false;
  
  hasErrors(){
    if (this.validationErrors.length > 0) {
      this.openDialog = true;
    }
  }

  closeDialog(type?: string) {
    this.openDialog = false;

    if (type === 'add') {
      this.isAddSuccess = false;
      this.resetFields();
      this.onClosed();
    } else if (type === 'edit'){
      this.isEditSuccess = false;
      this.resetFields();
      this.onClosed()
    }
  }

  isValidLPSTechEmail(email: any): boolean {
    return email.toLowerCase().endsWith('@lpstech.com');
  }

  isValidLPSTechEmailMsg() {
    return 'Please enter a valid email address ending with "@lpstech.com".'
  }

  saveResource() {
    console.log(this.resourceInfos, "resourceInfos")
    this.checkValidation();
    this.hasErrors();
    const datePipe = new DatePipe('en-US');
    const formValue: any = this.form.value;
    const formData = new FormData();
    let isMissingFiles:boolean = true;

    this.items.controls.forEach((item, index) => {
      const itemGroup = item as FormGroup;
      const file = itemGroup.get('fileupload')?.value;
      const date = itemGroup.get('calendar')?.value;
      const formattedDate: any = datePipe.transform(date, 'yyyy-MM-dd');

      if (file === null) {
        isMissingFiles = true;
      } else {
        isMissingFiles = false;
      }
  
      formData.append(`files`, file);
      formData.append('owner', this.resourceInfos.empId && this.resourceInfos.empId );
      formData.append(`name_${index}`, itemGroup.get('name')?.value);
      formData.append(`calendar_${index}`, formattedDate);
      formData.append(`id_${index}`, itemGroup.get('id')?.value)
      console.log(itemGroup.get('name')?.value, "<<<< item")
    })

    if(this.action !== '' && this.action === 'add') {
      this.resourceInfos.isEnabled = this.statusOption === 'Active' ? true : false
      if (!this.isValidLPSTechEmail(this.resourceInfos.emailAddress)) {
        console.log(this.isValidLPSTechEmail(this.resourceInfos.emailAddress), "this.isValidEmail")
        this.isValidEmail = true;
      } else {
        this.isValidEmail = false;
      }

      if (this.validationErrors.length === 0 && this.isValidLPSTechEmail(this.resourceInfos.emailAddress)) {
        this.isAddSuccess = true
        delete this.resourceInfos['status']
        this.addResourceService.addResource(this.resourceInfos)
        .subscribe((res: any) => {
          console.log(res, "<<<<<< RES")

        }, err => {
          console.log(err, "<<<<< ERROR")
        });
        console.log(isMissingFiles, "isMissingFiles")

        if (!isMissingFiles) {
          this.addResourceService.addResourceCertification(formData)
          .subscribe((res: any) => {
            console.log(res, "<<<<<< RES")
          }, err => {
            console.log(err, "<<<<< ERROR")
          });
        }
    }
    console.log("ADD", this.resourceInfos)
    } else if (this.action !== '' && this.action === 'edit') {
      console.log("edit", this.resourceInfos)
      const _resourceInfos: ResourceInfosInterface = {
        lastname: this.resourceInfos.lastname ||  '',
        firstname: this.resourceInfos.firstname ||  '',
        middlename: this.resourceInfos.middlename ||  '',
        suffix: this.resourceInfos.suffix ||  '',
        gender: this.resourceInfos.gender ||  '',
        emailAddress: this.resourceInfos.emailAddress ||  '',
        careerStep: this.resourceInfos.careerStep ||  '',
        empId: this.resourceInfos.empId ||  '',
        region: this.resourceInfos.region ||  '',
        role: this.resourceInfos.role ||  '',
        team: this.resourceInfos.team ||  '',
        isEnabled: this.resourceInfos.isEnabled ||  '',
        status: this.resourceInfos.isEnabled === true ? 'Active' : 'Inactive',
        skills: this.resourceInfos.skills ||  ''
      }
      
      _resourceInfos.isEnabled = this.statusOption === 'Active' ? true : false
      if (!this.isValidLPSTechEmail(_resourceInfos.emailAddress)) {
        console.log(this.isValidLPSTechEmail(_resourceInfos.emailAddress), "this.isValidEmail")
        this.isValidEmail = true;
      } else {
        this.isValidEmail = false;
      }
      if (this.validationErrors.length === 0 && this.isValidLPSTechEmail(_resourceInfos.emailAddress)) {
        this.isEditSuccess = true
        console.log(this.isEditSuccess, "success edited")
        delete _resourceInfos['status']
        this.addResourceService.editResource(this.resourceInfos.memberId, _resourceInfos)
        .subscribe((res: any) => {
          console.log(res, "<<<<<< RES")
        }, err => {
          console.log(err, "<<<<< ERROR")
        });


        for (var pair of formData.entries()) {
          console.log('FORM DATA EDIT',pair[0]+ ', ' + pair[1]); 
      }

        if (!isMissingFiles) {
          console.log('EDITING CERT')
          this.addResourceService.editResourceCertification(formData)
          .subscribe((res: any) => {
            console.log(res, "<<<<<< RES")
          }, err => {
            console.log(err, "<<<<< ERROR")
          });
        }

      }
    }
  }

  getSkills() {
    this.addResourceService.getSkills()
    .subscribe((res: any) => {
      console.log(res, "<<<<<< SKILLS")
      if(res) {
        res.map(({skillId, skillName} :any ) => (
          this.skillList.push({
            value: skillId ? skillId : '',
            label: skillName ? skillName : '',
          })
        ))
      }
    }, err => {
      this.skillList=[{
        value: '',
        label: ''
      }]
      console.log(err, "<<<<< ERROR")
    });
  }
}
