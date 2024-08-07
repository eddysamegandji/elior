import type Article from './article'
import type Expense from './expense'

export default class Accounting {
  constructor(
    public id: number | null,
    public eventName: string,
    public eventDate: Date | null,
    public employeeName: string,
    public flooz: number,
    public tmoney: number,
    public unpaid: number,
    public organizerPercent: number,
    public articles: Article[],
    public expenses: Expense[]
  ) {}
}
