export interface AddOn {
    id: number;
    name: string;
    description: string;
    cost: number;
    isConfirmed: boolean;
    organizationId: string;
    type: string;
}