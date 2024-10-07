import {InstitutionType} from "../enums/institution-type";

export interface InstitutionDto {
  institutionId:number;
  institutionName: string;
  description: string;
  address: string;
  website: string;
  institutionType: InstitutionType;
}
