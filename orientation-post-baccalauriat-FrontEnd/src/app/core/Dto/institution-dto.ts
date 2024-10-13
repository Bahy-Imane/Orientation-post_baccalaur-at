import {InstitutionType} from "../enums/institution-type";

export interface InstitutionDto {
  institutionId: number;
  institutionName: string;
  description: string;
  address: string;
  website: string;
  institutionLogo: string;
  averageRating: number;
  institutionType: InstitutionType;
}

