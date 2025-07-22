export interface Owner {
  id: string;
  name: string;
  accountNumber: string;
  level: number;
}

export interface Resource {
  id: string;
  owners: Owner[];
}

export interface Service {
  id: string;
  resources: Resource[];
}
