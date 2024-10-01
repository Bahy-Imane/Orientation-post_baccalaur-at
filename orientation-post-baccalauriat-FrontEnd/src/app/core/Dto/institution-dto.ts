import {InstitutionType} from "../enums/institution-type";

export interface InstitutionDto {
  institutionName: string;
  description: string;
  address: string;
  website: string;
  institutionType: InstitutionType;
}
