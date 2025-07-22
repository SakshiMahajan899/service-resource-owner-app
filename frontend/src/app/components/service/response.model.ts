export interface BackendResponse<T> {
  success: boolean;
  data: T;
  message: string;
}
