import { TrainingLinksModel } from "./training-links-model";
export class AddTrainingModel extends TrainingLinksModel {
    id?: string;
    trainingName?: string;
    trainingType?: string;
    productName?: string;
    startDate?: Date;
    dueDate?: Date;
    preReq?: string;
    description?: string;
    // trainingLink?: string;
    trainingTags?: string;
    isRequired?: boolean;
    certification?: boolean;
    trCondition?: number;
    trConditionValue?: number;
    active?: boolean;
    //Certification_Dtl Table
    certId?: string;
    certName?: string;
    duration?: string;
    fee?: number;
    currency?: string;
    certLink?: string;
    expiryDate?: Date;
    renewable?: boolean;
    skillId?: string;

    trainingLinksLists: TrainingLinksModel[] = [];
    
}